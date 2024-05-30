import asyncio
import socket

import contract

SERVER_CONN_CAPACITY = 10


async def handle_client(client , addr, id):
    loop = asyncio.get_event_loop()
    ack = (await loop.sock_recv(client, contract.BUFFER_SIZE)).decode("utf8")
    await loop.sock_sendall(client, f"connected to server with{ack}".encode())
    request = None
    while True:
        request = (await loop.sock_recv(client, contract.BUFFER_SIZE)).decode("utf8")
        print(f"request form  client {id} addr:{addr} request:\n{request}\n")
        response = f"response to request: {request}\n"

        await loop.sock_sendall(client, response.encode("utf8"))
        if request == "quit":
            break
    client.close()


async def run_server():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((contract.SERVER_IP, contract.SERVER_PORT))
    server_socket.listen(SERVER_CONN_CAPACITY)
    server_socket.setblocking(False)
    
    loop = asyncio.get_event_loop()
    id: int = 0
    while True:
        client, addr = await loop.sock_accept(server_socket)
        id += 1
        loop.create_task(handle_client(client, addr, id))



if __name__ == "__main__":
    asyncio.run(run_server())
