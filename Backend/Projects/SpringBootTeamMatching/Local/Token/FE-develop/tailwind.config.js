/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}", // 여기에 tsx가 꼭 들어있어야 합니다!
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}