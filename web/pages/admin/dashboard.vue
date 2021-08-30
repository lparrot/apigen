<template>
  <v-container fluid>

    <v-row>
      <v-col v-for="(component, componentName, componentIndex) in health.components" :key="componentIndex" class="d-flex align-stretch">
        <v-card rounded width="100%">
          <v-card-title>
            <span>{{ componentName }}</span>
            <v-spacer></v-spacer>
            <span :class="getStatusClass(component.status)">{{ component.status }}</span>
          </v-card-title>

          <v-list>
            <v-list-item v-for="(details, detailsName, detailsIndex) in component.details" :key="detailsIndex + ' ' + componentIndex" dense>
              <v-list-item-content>
                <v-list-item-subtitle>{{ detailsName }}</v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-action-text>
                <span v-if="componentName === 'diskSpace' && typeof details === 'number'">{{ $utils.formatBytes(details) }}</span>
                <pre v-else-if="typeof details === 'object'">{{ details }}</pre>
                <span v-else>{{ details }}</span>
              </v-list-item-action-text>
            </v-list-item>
          </v-list>
        </v-card>
      </v-col>
    </v-row>

    <v-divider class="my-4"/>

    <v-row>
      <v-col cols="12" md="6" xl="3">
        <v-card>
          <v-card-title>Threads</v-card-title>
          <v-card-text>
            <dashboard-line :datasets="datasetThread" :items="threads" label-attribute="timestamp"/>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" md="6" xl="3">
        <v-card>
          <v-card-title>JVM (Heap)</v-card-title>
          <v-card-text>
            <dashboard-line :datasets="datasetJvm" :items="jvmInfos" label-attribute="timestamp"/>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" md="6" xl="3">
        <v-card>
          <v-card-title>JVM (Non heap)</v-card-title>
          <v-card-text>
            <dashboard-line :datasets="datasetJvm" :items="jvmNonHeapInfos" label-attribute="timestamp"/>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" md="6" xl="3">
        <v-card>
          <v-card-title>CPU Usage</v-card-title>
          <v-card-text>
            <dashboard-line :datasets="datasetCpuUsage" :items="cpuUsage" :options="{ yAxes: { suggestedMax: 100 }}" label-attribute="timestamp"/>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Action, Component, State, Vue } from 'nuxt-property-decorator'
import DashboardLine from '~/components/shared/DashboardLine.vue'
import { Context } from '@nuxt/types'

@Component({
  components: {
    DashboardLine,
  },
})
export default class PageAdminDashboard extends Vue {
  @State(state => state.actuator.cpuUsage) cpuUsage
  @State(state => state.actuator.threads) threads
  @State(state => state.actuator.jvmInfos) jvmInfos
  @State(state => state.actuator.jvmNonHeapInfos) jvmNonHeapInfos

  @Action('actuator/setCpuUsage') setCpuUsage
  @Action('actuator/setThreads') setThreads
  @Action('actuator/setJvmInfos') setJvmInfos
  @Action('actuator/setJvmNonHeapInfos') setJvmNonHeapInfos

  datasetCpuUsage = [
    { data: (item) => item.used, label: 'USED', color: '#78ceff' },
  ]

  datasetThread = [
    { data: (item) => item.deamon, label: 'WAITING', color: '#0079ae' },
    { data: (item) => item.live, label: 'LIVE', color: '#78ceff' },
  ]

  datasetJvm = [
    { data: (item) => item.used / 1000000, label: 'USED (MB)', color: '#0079ae' },
    { data: (item) => item.size / 1000000, label: 'SIZE (MB)', color: '#78ceff' },
  ]

  health = {}

  async asyncData (ctx: Context) {
    const res_health = await ctx.$axios.$get('/actuator/health')

    await ctx.store.dispatch('actuator/setCpuUsage')
    await ctx.store.dispatch('actuator/setThreads')
    await ctx.store.dispatch('actuator/setJvmInfos')
    await ctx.store.dispatch('actuator/setJvmNonHeapInfos')

    return {
      health: res_health,
    }
  }

  async fetch () {
    this.$socket.client.subscribe('/topic/metrics', async (message) => {
      await this.setCpuUsage()
      await this.setThreads()
      await this.setJvmInfos()
      await this.setJvmNonHeapInfos()
    }, { id: 'sub-metrics' })
  }

  destroyed () {
    if (this.$socket.client.connected) {
      this.$socket.client.unsubscribe('sub-metrics')
    }
  }

  getStatusClass (status) {
    if (status === 'DOWN') {
      return 'red--text'
    }
    return 'green--text'
  }
}
</script>
