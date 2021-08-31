import Vue, { VNode } from "vue";

declare global {
  namespace JSX {
    interface Element extends VNode {
    }

    interface ElementClass extends Vue {
    }

    interface IntrinsicElements {
      [elem: string]: any;
    }
  }

  declare type ConfirmDialogOption = {
    color?: string
    width?: number
    zIndex?: number
    okLabel?: string
    cancelLabel?: string
  }

}
