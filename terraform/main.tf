terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0.1"
    }
  }
}

provider "docker" {}

resource "docker_image" "file_tool" {
  name = "file-tool"

  build {
    context = "../"
    tag     = ["file-tool:latest"]
  }
}

resource "docker_container" "file_tool" {
  image = docker_image.file_tool.image_id
  name  = var.container_name

  ports {
    internal = 80
    external = var.external_port
  }
}
