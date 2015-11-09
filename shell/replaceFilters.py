fin = open('../filters.properties', 'r')
fout = open('filters.properties', 'w')

with open('filters.properties', 'w') as fout:
    with open('../filters.properties', 'r') as fin:
        for line in fin:
            fout.write(line.replace("'", "'" + '"' + "'" + '"' + "'"))
            
