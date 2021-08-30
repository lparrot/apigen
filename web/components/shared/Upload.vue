<template>
  <div class="d-inline-block">
    <form enctype="multipart/form-data" novalidate>

      <input ref="inputUpload" :multiple="multiple" class="d-none" type="file" @change="filesChange">

      <slot :delete-file="deleteFile" :files="model" :show-upload="handleShowUpload">
        <div @click="$refs.inputUpload.click()">
          <div class="dropbox">
            <p>
              Déposez votre fichier ici<br>ou cliquez pour parcourir... </p>
          </div>
        </div>
      </slot>

      <div v-if="!$utils.hasSlot(this, 'default') && model != null && (multiple && model.length > 0)">
        <hr>
        <template v-if="multiple">
          <h4>{{ model.length }} fichiers à envoyer</h4>
          <ul class="list-unstyled">
            <li v-for="(item,i) in model" :key="i">
              <button class="mr-1" type="button" @click="deleteFile(i)">
                <i class="fas fa-trash text-danger"></i>
              </button>
              {{ item.name }}
            </li>
          </ul>
        </template>

        <template v-else>
          <button class="mr-1" type="button" @click="deleteFile">
            <i class="fas fa-trash text-danger"></i>
          </button>
          {{ item.name }}
        </template>
      </div>
    </form>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Ref, VModel, Vue } from 'nuxt-property-decorator'

@Component
export default class Upload extends Vue {

  @Prop({ required: false, type: Boolean, default: false }) readonly multiple!: boolean

  @Ref('inputUpload') inputUpload: any

  @VModel() model!: File[] & File

  handleShowUpload () {
    this.inputUpload.click()
  }

  filesChange () {
    // handle file changes
    const fileList = this.inputUpload.files

    if (this.multiple) {
      if (!fileList.length) return

      // append the files to FormData
      for (let i = 0; i < fileList.length; i++) {
        const file = fileList[i]
        this.model.push(file)
      }

    } else {
      this.model = fileList[0]
    }
  }

  deleteFile (index) {
    if (this.multiple) {
      this.model.splice(index, 1)
    } else {
      this.model = null
    }
  }
}
</script>

<style lang="scss" scoped>
.dropbox {
  outline: 2px dashed grey; /* the dash box */
  outline-offset: -10px;
  background: lightcyan;
  color: dimgray;
  padding: 10px 10px;
  height: 100px;
  position: relative;
  cursor: pointer;
}

.dropbox:hover {
  background: lightblue; /* when mouse over to the drop zone, change color */
}

.dropbox p {
  font-size: 1.2em;
  text-align: center;
  padding: 15px 0;
}
</style>
