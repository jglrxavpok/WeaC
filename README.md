WeaC, or Wow not Even Arrays from C!
====
Contraction of Weak and C, WeaC is a langage targetted to be compiled to bytecode (later usable by the JVM).

Exemple of code:

'''c
#include <time>
#include <math>

double out2(int a,int b)
{
	a = 0;
	string c = "Test";
	out(a-b);
	out(c+"!"-"est");
	double d = pow(pow(2,2),2);
	return d;
}

int main()
{
	return out2(1,2);
}
'''

Generated output:
'''
METHOD_START __MAIN__::out2 double(int;int;)
LOAD_CONST 0
VAR_STORE 0
LOAD_CONST "Test"
VAR_STORE 2
VAR_LOAD 0
VAR_LOAD 1
OPERATION -
METHOD_CALL std::out *(*;)
VAR_LOAD 2
LOAD_CONST "!"
OPERATION +
LOAD_CONST "est"
OPERATION -
METHOD_CALL std::out *(*;)
LOAD_CONST 2
LOAD_CONST 2
METHOD_CALL math::pow *(int;int;)
LOAD_CONST 2
METHOD_CALL math::pow *(*;int;)
VAR_STORE 3
VAR_LOAD 3
RETURN
METHOD_END
METHOD_START __MAIN__::main int()
LOAD_CONST 1
LOAD_CONST 2
METHOD_CALL __MAIN__::out2 *(int;int;)
RETURN
METHOD_END
'''

Still doesn't handle POO nor structs nor arrays. (WIP)
