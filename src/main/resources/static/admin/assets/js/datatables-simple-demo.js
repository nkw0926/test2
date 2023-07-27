window.addEventListener('DOMContentLoaded', event => {
    // Simple-DataTables
    // https://github.com/fiduswriter/Simple-DataTables/wiki

    const datatablesSimple = document.getElementById('datatable');
    const datatablesSimpleC = document.getElementsByClassName('datatable');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
    if (datatablesSimpleC) {
        new simpleDatatables.DataTable(datatablesSimpleC);
    }
});
