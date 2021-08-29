import { Chart, registerables } from 'chart.js'
import { Context } from '@nuxt/types'
import { Inject } from '@nuxt/types/app'

export default async (ctx: Context, inject: Inject) => {
  Chart.register(...registerables)
}
