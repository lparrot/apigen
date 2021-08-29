<template>
  <v-app v-if="!$fetchState.pending" dark>
    <v-navigation-drawer v-model="drawer" app fixed>
      <v-list class="mx-2" dense>
        <v-list-item>
          <v-list-item-content>
            <v-btn class="text-h5 text-uppercase align-center justify-center" plain text to="/">API Generator</v-btn>
          </v-list-item-content>
        </v-list-item>

        <v-divider class="my-2"></v-divider>

        <v-btn :to="{name: 'content'}" block color="success">Add content</v-btn>

        <v-subheader>Collections</v-subheader>

        <v-list-item v-for="(content, contentIndex) in contents" :key="`content-${contentIndex}`" :to="{name: 'content-list', params: {idContent: content._id}}" exact router style="min-height: 26px">
          <v-icon class="mr-2" dense small>mdi-apps</v-icon>
          <v-list-item-content class="py-1">
            <v-list-item-title v-text="content.name"/>
          </v-list-item-content>
        </v-list-item>

        <v-subheader>Administration</v-subheader>

        <v-list-item v-for="(adminPage, adminPageIndex) in adminPages" :key="`adminPage-${adminPageIndex}`" :to="adminPage.to" exact router style="min-height: 26px">
          <v-icon class="mr-2" dense small>{{ adminPage.icon }}</v-icon>
          <v-list-item-content class="py-1">
            <v-list-item-title v-text="adminPage.text"/>
          </v-list-item-content>
        </v-list-item>

      </v-list>
    </v-navigation-drawer>

    <v-app-bar app dense fixed flat tile>
      <v-app-bar-nav-icon v-if="$vuetify.breakpoint.mdAndDown" @click.stop="drawer = !drawer"/>
      <v-spacer></v-spacer>

      <v-menu offset-y rounded>
        <template #activator="{ on, attrs }">
          <v-btn icon v-bind="attrs" v-on="on">
            <v-icon>mdi-cog</v-icon>
          </v-btn>
        </template>

        <v-card>
          <v-list dense>
            <v-list-item-group>
              <v-list-item link @click="$vuetify.theme.dark = !$vuetify.theme.dark">
                <v-list-item-icon>
                  <v-icon>mdi-weather-night</v-icon>
                </v-list-item-icon>

                <v-list-item-content>Light/Dark mode</v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>
      </v-menu>
    </v-app-bar>

    <v-main>
      <Nuxt/>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import { Action, Component, State, Vue } from 'nuxt-property-decorator'

@Component
export default class LayoutDefault extends Vue {

  @State('contents') contents: any[]
  @Action('getContents') getContents

  drawer = this.$vuetify.breakpoint.lgAndUp

  adminPages = [
    { text: 'Dashboard', to: '/admin/dashboard', icon: 'mdi-view-dashboard' },
    { text: 'Settings', to: '/admin/settings', icon: 'mdi-application-cog' },
    { text: 'Metrics', to: '/admin/metrics', icon: 'mdi-chart-line' },
    { text: 'Http traces', to: '/admin/traces', icon: 'mdi-cable-data' },
    { text: 'Journal', to: '/admin/events', icon: 'mdi-calendar' },
    { text: 'Logs', to: '/admin/logs', icon: 'mdi-math-log' },
    { text: 'Tasks', to: '/admin/tasks', icon: 'mdi-calendar-check' },
  ]

  async fetch () {
    await this.getContents()
  }
}
</script>
