$(document).ready(function () {
    $("input[type='text'][name='image']").on("mouseover", function() {
      var id = $(this).attr('id');
      var image = $('#' + id).val();
      if (image) {
        $(".image-preview img").attr("src", image);
        $(".image-preview")
          .css({
            top: $(this).offset().top - $(".image-preview").height() - 10 + "px",
            left: $(this).offset().left + "px",
          })
          .fadeIn();
      } else {
        console.log("No image URL found.");
      }
    });

    $(".preview-image").on("mouseout", function () {
      $(".image-preview").fadeOut();
    });
  });