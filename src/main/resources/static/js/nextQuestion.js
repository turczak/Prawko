function toggleButton() {
        const nextQuestionButton = document.getElementById('nextQuestionButton');
        nextQuestionButton.disabled = !document.querySelector('input[name="answer"]:checked')
}
