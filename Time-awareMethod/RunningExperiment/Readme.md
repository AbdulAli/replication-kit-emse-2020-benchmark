RunningExperiment contains the code to run the time experiment on time-aware configuration dataset.

1. Copy everything in home folder to your home directory. 
2. The paths inside /home/benchmark/config/*.csv   need to be changed according to your machine.
3. Try to keep one .csv file in config folder, so that one experiment runs at a time. Otherwise sometimes the code will not execute all configurations.
4. While running experiment its safer to to limit your memory/ram to -Xmx 4GB, as for larger memory the threading code might skip some configurations execution.
5. Make sure the path in /home/generateOutputCsv.sh is correct
6. Copy all data inside AllConfigs to /home/Configs/
7. Make sure /home/benchmark/results-csv/ folder is empty
8. Make sure /home/benchmark/JURECZKO/Configs/ folder is empty
9. Simply run "codeWorkspace/ali-crosspare.jar /home/benchmark/config" and resultant ouput.csv will be produced in /home/benchmark/result-csv
10. The resultant file output.csv might have a missing header which you can add yourself.
HEADER = {technique,configuration,windowSize,time,project,size_test,size_training,succHe_DT,succZi_DT,succG75_DT,succG60_DT,error_DT,recall_DT,precision_DT,fscore,gscore,mcc,roc,aucec_DT,tpr_DT,tnr_DT,fpr_DT,fnr_DT,tp_DT,fn_DT,tn_DT,fp_DT}


