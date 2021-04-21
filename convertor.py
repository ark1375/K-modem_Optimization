#### PLY TO WKT CONVERTOR FOR NORMAL - UNHOLED POLYGONS

import os
import sys
import argparse


def main():

    ###     Parser Part
    parser = argparse.ArgumentParser(description = 'Polygon convertor v0.01')
    parser.add_argument('func', help ='Type of conversion', choices = ['ply_to_wkt'], metavar = '[function]')
    parser.add_argument('mos' , help = 'Multiple or Single conversion' , choices = ['m' , 's'] , metavar = '[Multiple - Single]')
    parser.add_argument("inpAdd" , help ="Input file-s address", metavar = '[input]' )
    parser.add_argument("outAdd", help ="Output file-s address", metavar = '[output]')
    parser.add_argument("-m" , help = "The Multiplier")
    parser.parse_args()
    ###     End Parser Part

    


def convert(path1):
    file = open(path1 , mode = 'r')
    file = file.readlines()[10:-1]
    file = [a[:-3] for a in file]
    file.append(file[0])
    out = ', '.join(file)
    out = 'POLYGON((' + out + '))'
    path = path1[:path1.rfind('.')]+'.wkt'
    file = open(path , 'w')
    file.write(out)
    file.close()
    
def convert_all(path):
    for (dirpath, dirnames, filenames) in walk(path):
        filenames = [path + '\\' + name for name in filenames]
        for name in filenames:
            convert(name)

if __name__ == '__main__':
    main()
    
