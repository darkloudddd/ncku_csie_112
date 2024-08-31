m = 0
num = int(input("Enter a decimal number: "))

if num == 0:
    print("0")

if num > 0:
    
    k = 0
    while 2**k <= num:
        k = k + 1

    x = k - 1 #最大到2的x次方

    while x >= 0:

        if num - 2**x >=0:
            num = num - 2**x
            m = m + 10**x
        x = x - 1

    print(m)
