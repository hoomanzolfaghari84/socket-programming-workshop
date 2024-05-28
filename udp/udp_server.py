# other examples at https://asyncudp.readthedocs.io/en/latest/

import asyncio
import contract


class UDPServer:

    async def send(self, addr, message):
        print(f"sending '{message}' to {addr}")
        self.transport.sendto(str(message).encode(), addr)

    def connection_made(self, transport):
        self.transport = transport

    def datagram_received(self, data, addr):
        loop = asyncio.get_event_loop()
        request = data.decode("utf8")
        print(f"got {request} from {addr}")
        message = f"server response to: {request}"
        loop.create_task(self.send(addr, message))


async def run_server():
    loop = asyncio.get_running_loop()
    await loop.create_datagram_endpoint(
        lambda: UDPServer(), local_addr=(contract.SERVER_IP, contract.SERVER_PORT)
    )
    while True:
        await asyncio.sleep(3600)


asyncio.run(run_server())
