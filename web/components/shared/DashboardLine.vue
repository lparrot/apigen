<template>
  <canvas ref="canvasElement"></canvas>
</template>

<script lang="ts">
import { Component, Prop, Ref, Vue, Watch } from 'nuxt-property-decorator'
import { Chart } from 'chart.js'
import get from 'lodash.get'

@Component
export default class DashboardLine extends Vue {

  @Prop() readonly items!: any[]
  @Prop() readonly labelAttribute!: string
  @Prop() readonly datasets: any[]
  @Prop() readonly options: any

  @Ref('canvasElement') canvasElement

  chart: Chart

  mounted () {
    this.updateChart()
  }

  async updateChart () {
    const data = {
      labels: this.items.map(item => get(item, this.labelAttribute)),
      datasets: this.datasets.map(dataset => {
        return {
          label: dataset.label,
          data: this.items.map(item => dataset.data(item)),
          borderColor: dataset.color,
          backgroundColor: dataset.color,
          fill: true,
          tension: 0.4,
          pointRadius: 0,
          ...dataset.props,
        }
      }),
    }

    if (this.chart == null) {
      this.chart = new Chart(this.canvasElement, {
        type: 'line',
        data,
        options: Object.assign({}, {
            plugins: {
              legend: {
                labels: {
                  color: this.$vuetify.theme.dark ? 'white' : '',
                },
              },
            },
            animation: {
              duration: 0,
            },
            responsive: true,
            interaction: {
              mode: 'index',
              intersect: false,
            },
            scales: {
              yAxes: {
                ticks: {
                  color: 'white',
                },
                beginAtZero: true,
                grid: {
                  display: false,
                },
              },
              xAxes: {
                display: false,
                grid: {
                  display: false,
                },
              },
            },
          },
          this.options,
        ),
      })
    } else {
      this.chart.data = data
    }

    this.chart.update()
  }

  @Watch('items', { immediate: false, deep: true })
  watchItems () {
    this.updateChart()
  }

}
</script>
