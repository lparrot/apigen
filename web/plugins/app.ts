import { Context } from '@nuxt/types'
import { Inject } from '@nuxt/types/app'
import PageContent from '~/components/routes/content.vue'
import PageContentList from '~/components/routes/content-list.vue'
import PageContentItem from '~/components/routes/content-item.vue'

export default async (ctx: Context, inject: Inject) => {
  ctx.app.router.addRoute({ name: 'content', path: '/contents/:idContent?', component: PageContent })
  ctx.app.router.addRoute({ name: 'content-list', path: '/contents/:idContent/list', component: PageContentList })
  ctx.app.router.addRoute({ name: 'content-item', path: '/contents/:idContent/items/:idItem?', component: PageContentItem })
}
