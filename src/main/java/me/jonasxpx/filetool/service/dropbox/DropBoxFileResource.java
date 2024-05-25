package me.jonasxpx.filetool.service.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import me.jonasxpx.filetool.exception.FileToolException;
import me.jonasxpx.filetool.service.FileResourceManagement;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DropBoxFileResource implements FileResourceManagement {

    private static final int UPLOAD_LIMIT_BYTES = 1024;
    private final DbxClientV2 dbxClientV2;

    public DropBoxFileResource(DbxClientV2 dbxClientV2) {
        this.dbxClientV2 = dbxClientV2;
    }

    @Override
    public Resource getFileById(String id) {
        return null;
    }

    @Override
    public void upload(final MultipartFile multipartFile) {
        try (final UploadSessionStartUploader session = dbxClientV2.files().uploadSessionStart()){
            long uploadReamaning = multipartFile.getSize();
            long offset = 0;

            final UploadSessionStartResult sessionUpload = session.uploadAndFinish(multipartFile.getInputStream(), UPLOAD_LIMIT_BYTES);

            uploadReamaning -= Math.min(multipartFile.getSize(), UPLOAD_LIMIT_BYTES);
            offset += Math.min(multipartFile.getSize(), UPLOAD_LIMIT_BYTES);

            while (uploadReamaning > 0) {
                final UploadSessionCursor uploadSessionCursor = new UploadSessionCursor(sessionUpload.getSessionId(), Math.min(multipartFile.getSize(), offset));

                try (final UploadSessionAppendV2Uploader uploadSessionAppendV2Uploader = dbxClientV2.files().uploadSessionAppendV2(uploadSessionCursor)) {
                    uploadSessionAppendV2Uploader.uploadAndFinish(multipartFile.getInputStream(), UPLOAD_LIMIT_BYTES);

                    uploadReamaning -= UPLOAD_LIMIT_BYTES;
                    offset += UPLOAD_LIMIT_BYTES;
                }
            }

            final UploadSessionCursor uploadSessionCursor = new UploadSessionCursor(sessionUpload.getSessionId(), offset);
            try (final UploadSessionFinishUploader uploadSessionFinishUploader =
                         dbxClientV2.files().uploadSessionFinish(uploadSessionCursor, CommitInfo.newBuilder("/" + multipartFile.getOriginalFilename()).build())) {
                uploadSessionFinishUploader.finish();
            }
        } catch (DbxException | IOException e) {
            throw new FileToolException(e);
        }
    }
}
