import Stomp, { Client } from 'webstomp-client'
import { Context } from '@nuxt/types'

export default async (ctx, inject) => {
  const socket = new SocketService(ctx)

  socket.createConnection()
  await socket.connect()

  inject('socket', socket)
}

export class SocketService {

  ctx: Context
  client: Client
  socket: WebSocket

  constructor (ctx: Context) {
    this.ctx = ctx
  }

  createConnection () {
    const url = ((window.location.protocol === 'https:') ? 'wss://' : 'ws://') + window.location.host + '/ws'
    this.client = Stomp.client(url, { debug: false })
  }

  disconnect () {
    this.client.disconnect()
  }

  async connect () {
    return new Promise((resolve, reject) => {
      this.client.connect(
        { server: 'api-generator' },
        frame => {
          resolve(this.client)
        },
        error => {
          reject(error)
        },
      )
    })
  }
}

declare module 'vue/types/vue' {
  interface Vue {
    readonly $socket: SocketService
  }
}

declare module '@nuxt/types' {
  interface NuxtAppOptions {
    readonly $socket: SocketService
  }

  interface Context {
    readonly $socket: SocketService
  }
}

declare module 'vuex/types/index' {
  interface Store<S> {
    readonly $socket: SocketService
  }
}
