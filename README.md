# DE, PSO Comparative study

The purpose of the project was to implement both 
the PSO and DE algorithms and perform a comparative study.

### Build instructions
```
mvn clean compile assembly:single
```

### Running

You must specify the algorithm to test by using -de or -pso as
a command line argument.

```
java -jar <path to jar>.jar <-de or -pso> -display <Benchmark name>
```

To run the benchmarks and output the test results
```
java -jar <path to jar>.jar <-de or -pso> -test
```
or
```
java -jar <path to jar>.jar <-de or -pso> -test <Benchmark name>
```

## Benchmark Examples

#### Differential Evolution

Ackley <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/DE/DE_Ackley.gif?raw=true)<br><br>
Bent Cigar <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/DE/DE_BentCigar.gif?raw=true)<br><br>
Discus <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/DE/DE_Discus.gif?raw=true)<br><br>
Griewank <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/DE/DE_Griewank.gif?raw=true)<br><br>
Rastrigin <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/DE/DE_Rastrigin.gif?raw=true)<br><br>
Rosenbrock <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/DE/DE_Rosenbrock.gif?raw=true)<br><br>

<br>

#### Particle swarm optimization
Ackley <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/PSO/PSO_Ackley.gif?raw=true)<br><br>
Bent Cigar <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/PSO/PSO_BentCigar.gif?raw=true)<br><br>
Discus <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/PSO/PSO_Discus.gif?raw=true)<br><br>
Griewank <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/PSO/PSO_Griewank.gif?raw=true)<br><br>
Rastrigin <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/PSO/PSO_Rastrigin.gif?raw=true)<br><br>
Rosenbrock <br>
![alt text](https://github.com/BradleyWood/DE-vs-PSO/blob/master/results/PSO/PSO_Rosenbrock.gif?raw=true)<br><br>