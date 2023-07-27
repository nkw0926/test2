// 나잇대별 파이 차트
function drawPieChartByAgeRange(data) {
    // Process the data to the format suitable for Highcharts pie chart
    const seriesData = data.map((ageRange) => ({
        name: Object.values(ageRange)[0],
        y: Object.values(ageRange)[1],
    }));
    Highcharts.chart("agePieChart", {
        chart: {
            type: "pie",
        },
        title: {
            text: "Age Ranges", // Chart title
        },
        series: [
            {
                name: "count",
                data: seriesData, // Data for the chart series
            },
        ],
    });
}

function fetchDataFromServerByAgeRange() {
    fetch("/admin/agerange/pieChart")
        .then((response) => response.json())
        .then((data) => {
            drawPieChartByAgeRange(data);
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
        });
}
// 성별 별 파이 차트
function drawPieChartBySex(data) {
    // Process the data to the format suitable for Highcharts pie chart
    const seriesData = data.map((ageRange) => ({
        name: Object.values(ageRange)[0],
        y: Object.values(ageRange)[1],
    }));

    Highcharts.chart("sexPieChart", {
        chart: {
            type: "pie",
        },
        title: {
            text: "Sex", // Chart title
        },
        series: [
            {
                name: "count:",
                data: seriesData, // Data for the chart series
            },
        ],
    });
}

function fetchDataFromServerBySex() {
    fetch("/admin/sex/pieChart")
        .then((response) => response.json())
        .then((data) => {
            drawPieChartBySex(data);
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
        });
}
$(document).ready(function () {
    // Call the function to fetch data from the server and draw the pie chart
    fetchDataFromServerByAgeRange();
    fetchDataFromServerBySex()
})


