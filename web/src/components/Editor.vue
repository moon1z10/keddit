<template>
  <quill-editor v-model:value="internalContent"></quill-editor>
</template>

<script>
import { reactive, ref, watch } from 'vue';

export default {
  name: 'App',
  props: {
    modelValue: String, // 상위 컴포넌트로부터 받은 v-model 값
  },
  setup(props, { emit }) {
    const internalContent = ref(props.modelValue);
    // internalContent가 변경될 때마다 상위 컴포넌트에 업데이트된 값을 emit
    watch(internalContent, (newValue) => {
      emit('update:modelValue', newValue);
    });

    const state = reactive({
      content: '',
      _content: '',
      editorOption: {
        placeholder: '',
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            ['blockquote', 'code-block'],
            [{ header: 1 }, { header: 2 }],
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ script: 'sub' }, { script: 'super' }],
            [{ indent: '-1' }, { indent: '+1' }],
            [{ direction: 'rtl' }],
            [{ size: ['small', false, 'large', 'huge'] }],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ color: [] }, { background: [] }],
            [{ font: [] }],
            [{ align: [] }],
            ['clean'],
            ['link', 'image', 'video']
          ]
        }
        // more options
      },
      disabled: false
    })

    const onEditorBlur = (quill) => {
      console.log('editor blur!', quill)
    }
    const onEditorFocus = (quill) => {
      console.log('editor focus!', quill)
    }
    const onEditorReady = (quill) => {
      console.log('editor ready!', quill)
    }
    const onEditorChange = ({ quill, html, text }) => {
      console.log('editor change!', quill, html, text)
      state._content = html
    }

    setTimeout(() => {
      state.disabled = true
    }, 2000)

    return { internalContent, state, onEditorBlur, onEditorFocus, onEditorReady, onEditorChange }
  }
}
</script>