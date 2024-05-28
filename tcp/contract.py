from enum import Enum


BUFFER_SIZE = 225
SERVER_PORT = 1234
SERVER_IP = "127.0.0.1"


class ProtocolDTypes(Enum):
    INTEGER = 0
    TEXT = 1
    PNG_FILE = 2


# class ProtoHeader:
#     def __init__(self, dTpye: int, bodyLength: int):
#         self.dType = dTpye
#         self.bodyLenght = bodyLength
