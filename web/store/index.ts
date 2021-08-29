import { ActionTree, GetterTree, MutationTree } from 'vuex'

export const state = () => ({
  contents: [],
})

export type RootState = ReturnType<typeof state>

export const getters: GetterTree<RootState, RootState> = {}

export const mutations: MutationTree<RootState> = {
  SET_CONTENTS (state, contents) {
    state.contents = contents
  },
}

export const actions: ActionTree<RootState, RootState> = {
  async getContents ({ commit }) {
    const contents = await this.$api.content.getAll()
    commit('SET_CONTENTS', contents)
  },
}
