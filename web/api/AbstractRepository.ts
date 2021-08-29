import { Context } from '@nuxt/types'
import { NuxtAxiosInstance } from '@nuxtjs/axios'

export class AbstractRepository {
  protected _ctx: Context
  protected _axios: NuxtAxiosInstance

  constructor (ctx: Context, axios: NuxtAxiosInstance) {
    this._ctx = ctx
    this._axios = ctx.$axios
  }
}
