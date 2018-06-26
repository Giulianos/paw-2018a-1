/* Function to scroll to the bottom of the message line (last messages) */

$(window).on('load', function() {
  /* Scroll to bottom */
  $(".message-line").scrollTop($(".message-line")[0].scrollHeight);
  /* Send with enter, newline with shift+enter */
  $("#new-message-text").keypress(function (e) {
    if(e.which == 13 && !e.shiftKey) {
        $(this).closest("form").submit();
        e.preventDefault();
        return false;
    }
  });
});
