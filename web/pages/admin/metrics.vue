<template>
  <v-container fluid>
    <v-select v-model="selectedName" :items="metricNames" dense filled hint="Select a name in the list to show metric informations" label="Metric name" persistent-hint @change="onMetricSelected"></v-select>

    <div v-if="selectedMetric != null" class="p-2 bg-white rounded mt-2">
      <template v-if="selectedMetric.description != null">
        <v-list>
          <v-list-item two-line>
            <v-list-item-content>
              <v-list-item-title v-text="selectedMetric.description"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>

        <v-divider class="my-4"></v-divider>

      </template>

      <template v-if="selectedMetric.availableTags.length > 0">
        <v-list>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>Tags</v-list-item-title>
              <v-list-item-content>
                <v-row>
                  <v-col v-for="(tag, tagIndex) in selectedMetric.availableTags" :key="tagIndex" cols="12" md="6">
                    <v-select v-model="selectedTags[tag.tag]" :items="tag.values" :label="tag.tag" clearable dense hint="Select a tag in the list to filter metrics" persistent-hint @change="onTagSelected(tag.tag)"></v-select>
                  </v-col>
                </v-row>
              </v-list-item-content>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </template>

      <v-divider class="my-4"></v-divider>

      <template v-if="selectedTagValue != null">
        <v-list>
          <v-list-item v-for="(measure, measureIndex) in selectedTagValue" :key="measureIndex">
            <v-list-item-content>
              <v-list-item-title v-text="measure.statistic"></v-list-item-title>
              <v-list-item-subtitle v-text="getMeasure(measure)"></v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </template>
    </div>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator'
import { Context } from '@nuxt/types'

@Component
export default class PageAdminMetrics extends Vue {
  metricNames = []
  selectedName = null
  selectedTags = {}
  selectedTagValue = null
  selectedMetric = null


  async asyncData (ctx: Context) {
    const res = await ctx.$api.admin.getMetrics()
    return {
      metricNames: res.names,
    }
  }

  async onMetricSelected () {
    this.selectedTags = {}
    this.selectedTagValue = null

    this.selectedMetric = await this.$axios.$get('/actuator/metrics/' + this.selectedName)
    this.selectedTagValue = this.selectedMetric.measurements
  }

  async onTagSelected () {
    if (this.selectedTags != null) {
      const tags = Object.keys(this.selectedTags)
        .filter(tag => this.selectedTags[tag] != null)
        .map(tag => `tag=${ tag }:${ this.selectedTags[tag] }`)
        .join('&')
      const res_metric_tag_value = await this.$axios.$get(`/actuator/metrics/${ this.selectedName }?${ encodeURI(tags) }`)
      this.selectedTagValue = res_metric_tag_value.measurements
    }
  }

  getMeasure (measure) {
    let result

    if ([ 'COUNT', 'ACTIVE_TASKS' ].indexOf(measure.statistic) > -1) {
      return measure.value
    }

    switch (this.selectedMetric.baseUnit) {
      case 'bytes':
        result = this.$utils.formatBytes(measure.value)
        break
      case 'nanoseconds':
      case 'microseconds':
      case 'milliseconds':
      case 'seconds':
        result = (measure.value as number).toFixed(2)
        break
      default:
        result = measure.value
        break
    }

    return this.selectedMetric.baseUnit != null ? `${ result } ${ this.selectedMetric.baseUnit }` : result
  }
}
</script>
