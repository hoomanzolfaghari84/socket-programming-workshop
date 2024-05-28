import asyncio
import asyncudp

import contract


async def run_client():
    sock = await asyncudp.create_socket(
        remote_addr=(contract.SERVER_IP, contract.SERVER_PORT)
    )

    while True:
        request = input("enter request\n")
        if request == "quit":
            break
        sock.sendto(request.encode("utf8"))
        data, addr = await sock.recvfrom()
        print(f"got {data.decode()} from {addr}")

    sock.close()


asyncio.run(run_client())
