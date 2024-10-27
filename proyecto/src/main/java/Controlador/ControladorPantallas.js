// controladorPantallas.js

function switchToVentaInicial() {
    document.getElementById("content").innerHTML = `<object type="text/html" data="ventaInicial.html" width="100%" height="600px"></object>`;
}

function switchToVentaIngresoUsuario() {
    document.getElementById("content").innerHTML = `<object type="text/html" data="ventaIngresoUsuario.html" width="100%" height="600px"></object>`;
}

function switchToPerfilUsuario() {
    document.getElementById("content").innerHTML = `<object type="text/html" data="perfilUsuario.html" width="100%" height="600px"></object>`;
}

function switchToMuroPublicaciones() {
    document.getElementById("content").innerHTML = `<object type="text/html" data="muroPublicaciones.html" width="100%" height="600px"></object>`;
}

function switchToPublicacion() {
    document.getElementById("content").innerHTML = `<object type="text/html" data="publicacion.html" width="100%" height="600px"></object>`;
}
