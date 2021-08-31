import { Context } from '@nuxt/types'
import { Inject } from '@nuxt/types/app'
import get from 'lodash.get'
import { DataOptions, DataPagination } from 'vuetify'

export default async (ctx: Context, inject: Inject) => {
  inject('utils', new Utils())
}

class Utils {
  convertPagination (options: Partial<DataOptions>) {
    let result = {
      page: options.page - 1 < 0 ? 0 : options.page - 1,
      size: options.itemsPerPage,
      sort: null,
    }

    if (options.sortBy != null && options.sortBy.length > 0) {
      result.sort = options.sortBy[0].concat(options.sortDesc[0] ? ',desc' : '')
    }

    return result
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

  formatBytes (bytes, decimals = 2) {
    if (bytes === 0) return '0 Bytes'

    const k = 1024
    const dm = decimals < 0 ? 0 : decimals
    const sizes = [ 'Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB' ]

    const i = Math.floor(Math.log(bytes) / Math.log(k))

    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i]
  }

  get (data: any, field: any[] | string, defaultValue: any = '') {
    if (typeof field === 'string') {
      return get(data, field, defaultValue)
    }
    return get(data, field.join("."), defaultValue)
  }

  hasSlot (instance, name = 'default') {
    return !!instance.$slots[name] || !!instance.$scopedSlots[name]
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
