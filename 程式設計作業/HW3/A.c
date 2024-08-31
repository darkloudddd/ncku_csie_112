#include <stdio.h>
#include <math.h>

int main()
{
    double a,b,c;
    scanf("(%lf,%lf,%lf)",&a,&b,&c);
    
    double s = ( a + b + c ) / 2;
    long double ans = sqrt(s*(s-a)*(s-b)*(s-c));
    
    printf("Area of triangle (%d,%d,%d) is %d.\n",(int)a,(int)b,(int)c,(int)ans);
    
    return 0;
}
