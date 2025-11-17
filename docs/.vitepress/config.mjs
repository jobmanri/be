import {defineConfig} from 'vitepress'
import {sidebar} from './sidebar-generator.mjs'

export default defineConfig({
  title: "잡만리 BE 문서화",
  description: "잡만리 BE 문서화",

  themeConfig: {
    sidebar: sidebar,
    nav: [
      {text: 'Home', link: '/'},
      {text: 'API Docs', link: '/api/index.md'}
    ],
  }
})