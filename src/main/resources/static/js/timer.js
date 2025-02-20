document.addEventListener("DOMContentLoaded", function () {
    const timerElement = document.getElementById('timer');
    if(!timerElement) return;
    const startTimeStr = timerElement.getAttribute('data-start-time');
    if(!startTimeStr) return;
    startTimer(startTimeStr, 25 * 60 * 1000);
})
function startTimer(startTimeStr, duration){
    const startTime = new Date(startTimeStr);
    function updateTimer(){
        const now = new Date();
        const endTime = new Date(startTime.getTime() + duration);
        const timeRemaining = endTime - now;
    if(timeRemaining > 0){
        const minutes = Math.floor((timeRemaining / (1000 * 60)) % 60);
        const seconds = Math.floor((timeRemaining / 1000) % 60);
        document.getElementById('timer').textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    }else{
        document.getElementById('timer').textContent = "Czas minął.";
        window.location.href = '/exam/result';
        }
    }
setInterval(updateTimer, 1000);
updateTimer();
}
