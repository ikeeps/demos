require 'sanitize'
require 'uri'

var_pattern = /\[\s*[a-zA-Z\.0-9]+\s*\]/

image_url_var_transformer = lambda do |env|
    return unless env[:node_name] == 'img'
    node = env[:node]
    url = node['src']
    return unless url =~ var_pattern
    Sanitize.node!(node, Sanitize::Config.merge(Sanitize::Config::RELAXED,
        :elements => %w[img]
    ))
    if node['src'] # sanitize allowed
        node['src'] = URI.decode(node['src']) # recover [ var ]
    end
    {:node_whitelist => [node]}
end

link_url_var_transformer = lambda do |env|
    return unless env[:node_name] == 'a'
    node = env[:node]
    url = node['href']
    return unless url =~ var_pattern
    Sanitize.node!(node, Sanitize::Config.merge(Sanitize::Config::RELAXED,
        :elements => %w[a],
        :attributes => {
            'a' => Sanitize::Config::RELAXED[:attributes]['a'] + ['target']
        }
    ))
    if node['href'] # sanitize allowed
        node['href'] = URI.decode(node['href']) # recover [ var ]
    end
    {:node_whitelist => [node]}
end

html = File.read("/media/sf_share/cart.html")
parsed = Sanitize.document(html, Sanitize::Config.merge(Sanitize::Config::RELAXED, 
:allow_comments => true,
:allow_doctype => true,
:elements => Sanitize::Config::RELAXED[:elements] + ['meta'],
:attributes => Sanitize::Config.merge(Sanitize::Config::RELAXED[:attributes],
    'meta' => ['name', 'content', 'http-equiv'],
    'body' => ['marginwidth', 'marginheight', 'leftmargin', 'topmargin'],
    'td' => Sanitize::Config::RELAXED[:attributes]['td'] + ['height', 'bgcolor'],
),
:css => {
    :properties => Sanitize::Config::RELAXED[:css][:properties] + ['mso-table-lspace', 'mso-table-rspace']
},
:transformers => [image_url_var_transformer, link_url_var_transformer]
));
File.write("/media/sf_share/cart-parsed.html", parsed)