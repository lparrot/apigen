import { Context } from '@nuxt/types'
import { Inject } from '@nuxt/types/app'
import PageContent from '~/components/routes/content.vue'
import PageContentList from '~/components/routes/content-list.vue'
import PageContentItem from '~/components/routes/content-item.vue'
import { ConfirmDialogService } from '~/services/confirm-dialog.service'
import ToastInterface from 'vue-toastification/src/ts/interface'

export default async (ctx: Context, inject: Inject) => {
  ctx.app.router.addRoute({ name: 'content', path: '/contents/:idContent?', component: PageContent })
  ctx.app.router.addRoute({ name: 'content-list', path: '/contents/:idContent/list', component: PageContentList })
  ctx.app.router.addRoute({ name: 'content-item', path: '/contents/:idContent/items/:idItem?', component: PageContentItem })

  inject('confirm', new ConfirmDialogService(ctx))
}

declare module 'vue/types/vue' {
  interface Vue {
    readonly $confirm: ConfirmDialogService
  }
}

declare module '@nuxt/types' {
  interface NuxtAppOptions {
    readonly $confirm: ConfirmDialogService
  }

  interface Context {
    readonly $confirm: ConfirmDialogService
    readonly $toast: ReturnType<typeof ToastInterface>
  }
}

declare module 'vuex/types/index' {
  interface Store<S> {
    readonly $confirm: ConfirmDialogService
  }
}
