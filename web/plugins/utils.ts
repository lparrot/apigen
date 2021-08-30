import { Context } from '@nuxt/types'
import { Inject } from '@nuxt/types/app'
import get from 'lodash.get'

export default async (ctx: Context, inject: Inject) => {
  inject('utils', new Utils())
}

class Utils {
  formatBytes (bytes, decimals = 2) {
    if (bytes === 0) return '0 Bytes'

    const k = 1024
    const dm = decimals < 0 ? 0 : decimals
    const sizes = [ 'Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB' ]

    const i = Math.floor(Math.log(bytes) / Math.log(k))

    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i]
  }

  downloadFile (data, filename) {

    if (typeof data === 'object') {
      data = JSON.stringify(data)
    }

    const blob = new Blob([ data ])
    if (navigator.msSaveBlob) {
      navigator.msSaveBlob(blob, filename)
    } else {
      const linkUrl = window.URL.createObjectURL(blob)
      const link = document.createElement('a')
      link.href = linkUrl
      link.setAttribute('download', filename)
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
    }
  }

  hasSlot (instance, name = 'default') {
    return !!instance.$slots[name] || !!instance.$scopedSlots[name]
  }

  get (data: any, field: string, defaultValue: any = '') {
    return get(data, field, defaultValue)
  }
}

declare module 'vue/types/vue' {
  interface Vue {
    readonly $utils: Utils
  }
}

declare module '@nuxt/types' {
  interface NuxtAppOptions {
    readonly $utils: Utils
  }

  interface Context {
    readonly $utils: Utils
  }
}

declare module 'vuex/types/index' {
  interface Store<S> {
    readonly $utils: Utils
  }
}
