import requests as req
import os

path={
        "mandarin-admin": [
            "/admin/about",
            "/admin/index",
            "/admin/login",
            "/admin/register",
            "/admin/search",
            "/admin/setting",
        ],
        "mandarin-librarian": [
            "/librarian/add",
            "/librarian/bookAdd",
            "/librarian/bookAddInternal",
            "/librarian/bookDelete",
            "/librarian/bookManage",
            "/librarian/bookManage2",
            "/librarian/bookUpdate",
            "/librarian/category",
            "/librarian/deleteReader",
            "/librarian/history",
            "/librarian/ifoModification",
            "/librarian/index",
            "/librarian/lendbookreturn",
            "/librarian/login",
            "/librarian/register",
            "/librarian/totalDeposit",
            "/librarian/totalFine",
            "/librarian/totalIncome",
            "/librarian/update",
            "/librarian/updateReader",
        ],
        }

for key, uris in path.items():
    for uri in uris:
        url="http://localhost:8080/"+uri
        resp=req.get(url)
        text=resp.text
        text=text.replace('href="/', 'href="')
        text=text.replace("href='/", "href='")
        text=text.replace('src="/', 'src="')
        text=text.replace('/api', 'https://virtserver.swaggerhub.com/tanglizi/MandarinAPI/1.0/api')

        links=path[key].copy()
        links.sort(key=lambda x: -len(x))
        for link in links:
            link=link[1:]
            text=text.replace(link, link.split('/')[-1]+'.html')

        open(os.path.join(key, uri.split('/')[-1]+'.html'), 'w').write(text)
        print(url)
