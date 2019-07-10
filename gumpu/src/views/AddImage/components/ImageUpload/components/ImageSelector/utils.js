export const fitImage = (ctx, img, w, h) => {
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