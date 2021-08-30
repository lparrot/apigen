import { ApiContent } from '~/api/ApiContent'
import { ApiList } from '~/api/ApiList'
import { ApiItem } from '~/api/ApiItem'
import { ApiContentField } from '~/api/ApiContentField'
import { ApiRelation } from '~/api/ApiRelation'

export const factories = {
  content: ApiContent.prototype,
  content_fields: ApiContentField.prototype,
  item: ApiItem.prototype,
  list: ApiList.prototype,
  relation: ApiRelation.prototype,

}

export default async (ctx, inject) => {
  Object.keys(factories).forEach(key => factories[key] = Reflect.construct(factories[key].constructor, [ ctx ]))
  inject('api', factories)
}

declare module 'vue/types/vue' {
  interface Vue {
    readonly $api: typeof factories
  }
}

declare module '@nuxt/types' {
  interface NuxtAppOptions {
    readonly $api: typeof factories
  }

  interface Context {
    readonly $api: typeof factories
  }
}

declare module 'vuex/types/index' {
  interface Store<S> {
    readonly $api: typeof factories
  }
}
