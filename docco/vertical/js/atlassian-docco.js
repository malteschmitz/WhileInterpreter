$(document).ready(function () {

    function initThemes(){
        var menu = $("#themeDropdown");
        menu.on("click","li > a.themeLink",function(event){
            switchStylesheet($(this).text());
        });

        menu.append('<li class="nav-header">Light</li>');

        $(".codebrush.light").each(function(index){
            menu.append('<li><a class="themeLink" href="#">' + $(this).attr("title") + '</a></li>');
        });

        menu.append('<li class="nav-header">Dark</li>');

        $(".codebrush.dark").each(function(index){
            menu.append('<li><a class="themeLink" href="#">' + $(this).attr("title") + '</a></li>');
        });
    }

    function switchStylesheet(styleName)
    {
        $('link.codebrush').each(function(i)
        {
            this.disabled = true;
            if($(this).attr('title') == styleName)
            {
                this.disabled = false;
            }
        });

        $('#themeDropdown > li').each(function(i)
        {
            $(this).removeClass('active');

            if($('a.themeLink',this).text() == styleName)
            {
                $(this).addClass('active');
            }
        });

        $.cookie("doccoCodeTheme",styleName,{path:'/',expires:365});

    }

    function initHiddenCode()
    {
        $.each($('.docco-section'), function() {
            var $this = $(this);
            var code = $('pre', $this);
            var lines = code.text().split('\n');
            $('.linecount', $this).text(lines.length);
            $('.hidden-code, .hidden-doc', $this).hide();
        });

        $('.hidden-code-toggle').click(function (e) {
            var $target = $(e.target);

            var $section = $("#section-" + $target.attr("index"));
            $(".hidden-code, .hidden-doc", $section).slideToggle("slow", function() {
                var $this = $(this);
                if ($this.is(":visible")) {
                    var newText = $target.html().replace("Show","Hide");
                    $target.html(newText);
                }
                else {
                    var newText = $target.html().replace("Hide","Show");
                    $target.html(newText);
                }
            });
        });
    }

    var myTheme = $.cookie("doccoCodeTheme");
    if(null == myTheme)
    {
        myTheme = "IDEA";
    }

    initThemes();
    switchStylesheet(myTheme);
    hljs.initHighlightingOnLoad();
    initHiddenCode();

});

