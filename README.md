# invistron website content source

## validate product files

```
sudo apt-get install yamllint
cd content/product 
yamllint ./*.md
```

### ToDo

There should be yaml schema checking mechanism imposed on the `content/product/*.md` files.
The colon symbol is not allowed without quote.
