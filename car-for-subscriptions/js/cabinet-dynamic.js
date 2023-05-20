function labelthumbs(json) {

    document.write('<ul id="label_with_thumbs">');
    for (let i = 0; i < numposts; i++) {
        let entry = json.feed.entry[i];

        let posttitle = entry.title.$t;

        let posturl;
        if (i == json.feed.entry.length) break;
        for (const element of entry.link) {
            if (element.rel == 'replies' && element.type == 'text/html') {
                let commenttext = element.title;
                let commenturl = element.href;
            }

            if (element.rel == 'alternate') {
                posturl = element.href;
                break;
            }
        }
        let thumburl;
        try {
            thumburl = entry.media$image.url;
        } catch (error)

        {
            s = entry.content.$t;
            a = s.indexOf("<img");
            b = s.indexOf("src=\"", a);
            c = s.indexOf("\"", b + 5);
            d = s.substr(b + 5, c - b - 5);
            if ((a != -1) && (b != -1) && (c != -1) && (d != "")) {
                thumburl = d;
            } else thumburl = 'http://3.bp.blogspot.com/-zP87C2q9yog/UVopoHY30SI/AAAAAAAAE5k/AIyPvrpGLn8/s1600/picture_not_available.png';
        }

        let postdate = entry.published.$t;

        let cdyear = postdate.substring(0, 4);

        let cdmonth = postdate.substring(5, 7);

        let cdday = postdate.substring(8, 10);

        let monthnames = new Array();
        monthnames[1] = "Jan";
        monthnames[2] = "Feb";
        monthnames[3] = "Mar";
        monthnames[4] = "Apr";
        monthnames[5] = "May";
        monthnames[6] = "June";
        monthnames[7] = "July";
        monthnames[8] = "Aug";
        monthnames[9] = "Sept";
        monthnames[10] = "Oct";
        monthnames[11] = "Nov";
        monthnames[12] = "Dec";
        document.write('<li class="clearfix">');
        if (showpostthumbnails == true)

            document.write('<strong><a href="' + posturl + '" target ="_top">' + posttitle + '</a></strong><br> <a href="' + posturl + '" target ="_top"><img class="label_thumb" src="' + thumburl + '"/></a>');
        document.write('');
        if ("content" in entry) {
            let postcontent = entry.content.$t;
        } else

        if ("summary" in entry) {
            let postcontent = entry.summary.$t;
        } else let postcontent = "";
        let re = /<\S[^>]*>/g;
        postcontent = postcontent.replace(re, "");

        if (showpostsummary == true) {
            if (postcontent.length < numchars) {
                document.write('');
                document.write(postcontent);
                document.write('');
            } else {
                document.write('');
                postcontent = postcontent.substring(0, numchars);

                let quoteEnd = postcontent.lastIndexOf(" ");
                postcontent = postcontent.substring(0, quoteEnd);
                document.write(postcontent + '...');
                document.write('');
            }
        }

        let towrite = '';
        let flag = 0;
        document.write('<br>');
        if (showpostdate === true) {
            towrite = towrite + monthnames[parseInt(cdmonth, 10)] + '-' + cdday + ' - ' + cdyear;
            flag = 1;
        }

        if (showcommentnum === true)

        {
            if (flag == 1) {
                towrite = towrite + ' | ';
            }

            if (commenttext == '1 Comments') commenttext = '1 Comment';
            if (commenttext == '0 Comments') commenttext = 'No Comments';
            commenttext = '<a href="' + commenturl + '" target ="_top">' + commenttext + '</a>';
            towrite = towrite + commenttext;
            flag = 1;;
        }

        if (displaymore === true)

        {
            if (flag === 1) towrite = towrite + ' | ';
            towrite = towrite + '<a href="' + posturl + '" class="url" target ="_top">More »</a>';
            flag = 1;;
        }

        document.write(towrite);
        document.write('</li>');
        if (displayseparator === true)

            if (i !== (numposts - 1))

                document.write('');
    }
    document.write('</ul>');
}

  //]]>