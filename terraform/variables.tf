variable "container_name" {
  description = "Value of the container name"
  type        = string
  default     = "file-tool"
}

variable "external_port" {
  description = "Exposed port from container"
  type        = number
  default     = 8000
}