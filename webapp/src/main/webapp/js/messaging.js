/* Function to scroll to the bottom of the message line (last messages) */

$(window).on('load', function() {
 $(".message-line").scrollTop($(".message-line")[0].scrollHeight);
});
