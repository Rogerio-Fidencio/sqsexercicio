# Diferenças entre SQS e SNS

O serviço SQS usa um sistema de Fila diferente do SNS que usa um modelo de pub/sub, além disso o SQS permite que sistemas consumam as mensagens em uma "pull", por outro lado o SNS envia as mensagens para seus assinantes, sem contar que as mensagens no SQS são persistentes, já no 
SNS não são.
