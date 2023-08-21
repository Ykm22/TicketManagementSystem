export function deleteLoader(){
    const loader = document.querySelector("#loader");
    loader.style.display = "none";
    const container = document.querySelector('.main-content-component');
    container.style.visibility = "visible";
    container.style.display = "block";
}

export function createLoader(){
    const loader = document.querySelector("#loader");
    loader.style.display = "block";
    const container = document.querySelector('.main-content-component');
    container.style.visibility = "hidden";
    container.style.display = "none";
}

// export function deleteLoaderAnalytics(){
//     const loader = document.querySelector("#loader");
//     loader.style.display = "none";
//     const container = document.querySelector('.main-content-component');
//     container.style.visibility = "visible";
//     container.style.display = "block";
// }

// export function createLoaderAnalytics(){
//     const loader = document.querySelector("#loader");
//     loader.style.display = "block";
//     const container = document.querySelector('.main-content-component');
//     container.style.visibility = "hidden";
//     container.style.display = "none";
// }