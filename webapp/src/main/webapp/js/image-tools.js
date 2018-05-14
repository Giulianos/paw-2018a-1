function fitImage(ctx, img, w, h) {

    var iw = img.width,
        ih = img.height;

    // calc source rectangle
    var sw = iw;
    var sh = ih;

    var sx = 0;
    var sy = 0;

    var dx = 0, dy = 0, dh, dw;

    var s_ar = sw / sh;
    var d_ar = w / h;

    // fill height or width
    if( s_ar > d_ar ) {
      // fill width
      dw = w;
      dh = (sh / sw) * dw;
      //center
      dy = (h - dh) / 2;
    } else {
      // fill height
      dh = h;
      dw = (sw / sh) * dh;
      //center
      dx = (w - dw) / 2;
    }
    //clean canvas
    ctx.clearRect(0, 0, w, h);
    // fill image in dest. rectangle
    ctx.drawImage(img, sx, sy, sw, sh,  dx, dy, dw, dh);
}

function loadImage() {
    var input, file, fr, img;

    if (typeof window.FileReader !== 'function') {
        write("The file API isn't supported on this browser yet.");
        return;
    }

    input = document.getElementById('imgfile');
    if (!input) {
        write("Um, couldn't find the imgfile element.");
    }
    else if (!input.files) {
        write("This browser doesn't seem to support the `files` property of file inputs.");
    }
    else if (!input.files[0]) {
        write("Please select a file before clicking 'Load'");
    }
    else {
        file = input.files[0];
        fr = new FileReader();
        fr.onload = createImage;
        fr.readAsDataURL(file);
    }

    function createImage() {
        img = new Image();
        img.onload = imageLoaded;
        img.src = fr.result;
    }

    function imageLoaded() {
        var canvas = document.getElementById("pub-image-canvas")
        var ctx = canvas.getContext("2d");
        fitImage(ctx, img, canvas.width, canvas.height);
        document.getElementById("image-field").value = canvas.toDataURL("image/png");
    }

    function write(msg) {
    }
}
