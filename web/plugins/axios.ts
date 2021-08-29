import { Context } from '@nuxt/types'
import { Inject } from '@nuxt/types/app'

export default async (ctx: Context, inject: Inject) => {
  ctx.$axios.download = _download.bind(ctx)
}

type AxiosDownloadConfig = {
  action?: 'get' | 'post' | 'put' | 'delete'
  data?: any
  mimetype?: string
}

async function _download (this: any, url: string, filename: string, config?: AxiosDownloadConfig) {
  config = Object.assign({ action: 'get', data: {} }, config)

  let httpOptions = {
    responseType: 'arraybuffer',
    headers: {
      Accept: config.mimetype,
    },
    params: null,
  }

  let res
  if (config.action === 'get') {
    httpOptions.params = config.data
    res = await this.$axios['$' + config.action](url, httpOptions)
  } else {
    res = await this.$axios['$' + config.action](url, config.data, httpOptions)
  }
  this.$utils.downloadFile(res, filename)
}

declare module '@nuxtjs/axios/types' {
  interface NuxtAxiosInstance {
    download?: typeof _download
  }
}
