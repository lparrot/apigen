import { ActionTree, GetterTree, MutationTree } from 'vuex'

export const state = () => ({
  cpuUsage: [],
  jvmInfos: [],
  jvmNonHeapInfos: [],
  threads: [],
})

export type RootState = ReturnType<typeof state>

export const getters: GetterTree<RootState, RootState> = {}

export const mutations: MutationTree<RootState> = {
  SET_CPU_USAGE (state, payload) {
    state.cpuUsage = payload
  },

  SET_JVM_INFOS (state, payload) {
    state.jvmInfos = payload
  },

  SET_JVM_NON_HEAP_INFOS (state, payload) {
    state.jvmNonHeapInfos = payload
  },

  SET_THREADS (state, payload) {
    state.threads = payload
  },
}

export const actions: ActionTree<RootState, RootState> = {
  async setCpuUsage ({ commit }) {
    commit('SET_CPU_USAGE', await this.$api.admin.getCpuUsage())
  },

  async setJvmInfos ({ commit }) {
    commit('SET_JVM_INFOS', await this.$api.admin.getJvmInfos(true))
  },

  async setJvmNonHeapInfos ({ commit }) {
    commit('SET_JVM_NON_HEAP_INFOS', await this.$api.admin.getJvmInfos(false))
  },

  async setThreads ({ commit }) {
    commit('SET_THREADS', await this.$api.admin.getThreadInfos())
  },
}
