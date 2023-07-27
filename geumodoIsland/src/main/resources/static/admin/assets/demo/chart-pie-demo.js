// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Pie Chart Example
// var ctx = document.getElementById("myPieChart");
// var myPieChart = new Chart(ctx, {
//   type: 'pie',
//   data: {
//     labels: ["20대 초반", "20대 후반", "30대 초반", "30대 후반"],
//     datasets: [{
//       data: [12.21, 15.58, 11.25, 8.32],
//       backgroundColor: ['#007bff', '#dc3545', '#ffc107', '#28a745'],
//     }],
//   },
// });

var ctx = document.getElementById("sexPieChart");
var myPieChart = new Chart(ctx, {
  type: 'pie',
  data: {
    labels: ["여성", "남성"],
    datasets: [{
      data: [12.21, 15.58],
      backgroundColor: [ '#dc3545','#007bff'],
    }],
  },
});

