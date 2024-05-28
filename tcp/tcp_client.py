import socket
import contract

s = socket.socket()

SERVER_IP = "127.0.0.1"
SERVER_PORT = 1234

s.connect((SERVER_IP, SERVER_PORT))
s.send("ack".encode())
print(s.recv(contract.BUFFER_SIZE).decode())

while True:

    send_message = input("enter text\n")
    s.send(send_message.encode())
    if send_message == "quit":
        break
    received_message = s.recv(contract.BUFFER_SIZE).decode()
    print("got message :", received_message)

s.close()
