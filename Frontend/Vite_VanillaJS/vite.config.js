import tailwindcss from "tailwindcss";
import autoprefixer from "autoprefixer";

export default {
    base: '/',
    css: {
        postcss: {
            plugins: [tailwindcss, autoprefixer],
        },
    }
};