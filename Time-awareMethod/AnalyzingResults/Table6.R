data<-read.csv("/home/hareem/EMSE-dataset/output.csv")
############################################### Fscore ############################################### 
meanfscores<-c()
i=1
C1<-output[output$configuration==1,]
meanfscores[i]<-mean(C1[C1$technique=="Amasaki15",]$fscore)

i=2
C2<-output[output$configuration==2,]
meanfscores[i]<-mean(C2[C2$technique=="Amasaki15",]$fscore)     

i=3
C3<-output[output$configuration==3,]
meanfscores[i]<-mean(C3[C3$technique=="Amasaki15",]$fscore)     

i=4
C4<-output[output$configuration==4,]
meanfscores[i]<-mean(C4[C4$technique=="Amasaki15",]$fscore)     

###Watanabe
i=5
C1<-output[output$configuration==1,]
meanfscores[i]<-mean(C1[C1$technique=="Watanabe08",]$fscore)

i=6
C2<-output[output$configuration==2,]
meanfscores[i]<-mean(C2[C2$technique=="Watanabe08",]$fscore)     

i=7
C3<-output[output$configuration==3,]
meanfscores[i]<-mean(C3[C3$technique=="Watanabe08",]$fscore)     

i=8
C4<-output[output$configuration==4,]
meanfscores[i]<-mean(C4[C4$technique=="Watanabe08",]$fscore)    

###CamargoCruz09
i=9
C1<-output[output$configuration==1,]
meanfscores[i]<-mean(C1[C1$technique=="CamargoCruz09",]$fscore)

i=10
C2<-output[output$configuration==2,]
meanfscores[i]<-mean(C2[C2$technique=="CamargoCruz09",]$fscore)     

i=11
C3<-output[output$configuration==3,]
meanfscores[i]<-mean(C3[C3$technique=="CamargoCruz09",]$fscore)     

i=12
C4<-output[output$configuration==4,]
meanfscores[i]<-mean(C4[C4$technique=="CamargoCruz09",]$fscore)    

###Nam15
i=13
C1<-output[output$configuration==1,]
meanfscores[i]<-mean(C1[C1$technique=="Nam15",]$fscore)

i=14
C2<-output[output$configuration==2,]
meanfscores[i]<-mean(C2[C2$technique=="Nam15",]$fscore)     

i=15
C3<-output[output$configuration==3,]
meanfscores[i]<-mean(C3[C3$technique=="Nam15",]$fscore)     

i=16
C4<-output[output$configuration==4,]
meanfscores[i]<-mean(C4[C4$technique=="Nam15",]$fscore)    

###Ma12
i=17
C1<-output[output$configuration==1,]
meanfscores[i]<-mean(C1[C1$technique=="Ma12",]$fscore)

i=18
C2<-output[output$configuration==2,]
meanfscores[i]<-mean(C2[C2$technique=="Ma12",]$fscore)     

i=19
C3<-output[output$configuration==3,]
meanfscores[i]<-mean(C3[C3$technique=="Ma12",]$fscore)     

i=20
C4<-output[output$configuration==4,]
meanfscores[i]<-mean(C4[C4$technique=="Ma12",]$fscore)    

############################################### MCC ############################################### 
meanMCC<-c()
i=1
C1<-output[output$configuration==1,]
meanMCC[i]<-mean(C1[C1$technique=="Amasaki15",]$mcc)

i=2
C2<-output[output$configuration==2,]
meanMCC[i]<-mean(C2[C2$technique=="Amasaki15",]$mcc)     

i=3
C3<-output[output$configuration==3,]
meanMCC[i]<-mean(C3[C3$technique=="Amasaki15",]$mcc)     

i=4
C4<-output[output$configuration==4,]
meanMCC[i]<-mean(C4[C4$technique=="Amasaki15",]$mcc)     

###Watanabe
i=5
C1<-output[output$configuration==1,]
meanMCC[i]<-mean(C1[C1$technique=="Watanabe08",]$mcc)

i=6
C2<-output[output$configuration==2,]
meanMCC[i]<-mean(C2[C2$technique=="Watanabe08",]$mcc)     

i=7
C3<-output[output$configuration==3,]
meanMCC[i]<-mean(C3[C3$technique=="Watanabe08",]$mcc)     

i=8
C4<-output[output$configuration==4,]
meanMCC[i]<-mean(C4[C4$technique=="Watanabe08",]$mcc)    

###CamargoCruz09
i=9
C1<-output[output$configuration==1,]
meanMCC[i]<-mean(C1[C1$technique=="CamargoCruz09",]$mcc)

i=10
C2<-output[output$configuration==2,]
meanMCC[i]<-mean(C2[C2$technique=="CamargoCruz09",]$mcc)     

i=11
C3<-output[output$configuration==3,]
meanMCC[i]<-mean(C3[C3$technique=="CamargoCruz09",]$mcc)     

i=12
C4<-output[output$configuration==4,]
meanMCC[i]<-mean(C4[C4$technique=="CamargoCruz09",]$mcc)    

###Nam15
i=13
C1<-output[output$configuration==1,]
meanMCC[i]<-mean(C1[C1$technique=="Nam15",]$mcc)

i=14
C2<-output[output$configuration==2,]
meanMCC[i]<-mean(C2[C2$technique=="Nam15",]$mcc)     

i=15
C3<-output[output$configuration==3,]
meanMCC[i]<-mean(C3[C3$technique=="Nam15",]$mcc)     

i=16
C4<-output[output$configuration==4,]
meanMCC[i]<-mean(C4[C4$technique=="Nam15",]$mcc)    

###Ma12
i=17
C1<-output[output$configuration==1,]
meanMCC[i]<-mean(C1[C1$technique=="Ma12",]$mcc)

i=18
C2<-output[output$configuration==2,]
meanMCC[i]<-mean(C2[C2$technique=="Ma12",]$mcc)     

i=19
C3<-output[output$configuration==3,]
meanMCC[i]<-mean(C3[C3$technique=="Ma12",]$mcc)     

i=20
C4<-output[output$configuration==4,]
meanMCC[i]<-mean(C4[C4$technique=="Ma12",]$mcc)  
############################################### AUC ############################################### 
meanROC<-c()
i=1
C1<-output[output$configuration==1,]
meanROC[i]<-mean(C1[C1$technique=="Amasaki15",]$roc)

i=2
C2<-output[output$configuration==2,]
meanROC[i]<-mean(C2[C2$technique=="Amasaki15",]$roc)     

i=3
C3<-output[output$configuration==3,]
meanROC[i]<-mean(C3[C3$technique=="Amasaki15",]$roc)     

i=4
C4<-output[output$configuration==4,]
meanROC[i]<-mean(C4[C4$technique=="Amasaki15",]$roc)     

###Watanabe
i=5
C1<-output[output$configuration==1,]
meanROC[i]<-mean(C1[C1$technique=="Watanabe08",]$roc)

i=6
C2<-output[output$configuration==2,]
meanROC[i]<-mean(C2[C2$technique=="Watanabe08",]$roc)     

i=7
C3<-output[output$configuration==3,]
meanROC[i]<-mean(C3[C3$technique=="Watanabe08",]$roc)     

i=8
C4<-output[output$configuration==4,]
meanROC[i]<-mean(C4[C4$technique=="Watanabe08",]$roc)    

###CamargoCruz09
i=9
C1<-output[output$configuration==1,]
meanROC[i]<-mean(C1[C1$technique=="CamargoCruz09",]$roc)

i=10
C2<-output[output$configuration==2,]
meanROC[i]<-mean(C2[C2$technique=="CamargoCruz09",]$roc)     

i=11
C3<-output[output$configuration==3,]
meanROC[i]<-mean(C3[C3$technique=="CamargoCruz09",]$roc)     

i=12
C4<-output[output$configuration==4,]
meanROC[i]<-mean(C4[C4$technique=="CamargoCruz09",]$roc)    

###Nam15
i=13
C1<-output[output$configuration==1,]
meanROC[i]<-mean(C1[C1$technique=="Nam15",]$roc)

i=14
C2<-output[output$configuration==2,]
meanROC[i]<-mean(C2[C2$technique=="Nam15",]$roc)     

i=15
C3<-output[output$configuration==3,]
meanROC[i]<-mean(C3[C3$technique=="Nam15",]$roc)     

i=16
C4<-output[output$configuration==4,]
meanROC[i]<-mean(C4[C4$technique=="Nam15",]$roc)    

###Ma12
i=17
C1<-output[output$configuration==1,]
meanROC[i]<-mean(C1[C1$technique=="Ma12",]$roc)

i=18
C2<-output[output$configuration==2,]
meanROC[i]<-mean(C2[C2$technique=="Ma12",]$roc)     

i=19
C3<-output[output$configuration==3,]
meanROC[i]<-mean(C3[C3$technique=="Ma12",]$roc)     

i=20
C4<-output[output$configuration==4,]
meanROC[i]<-mean(C4[C4$technique=="Ma12",]$roc)  

############################################### Gscore ############################################### 
meanGscore<-c()
i=1
C1<-output[output$configuration==1,]
meanGscore[i]<-mean(C1[C1$technique=="Amasaki15",]$gscore)

i=2
C2<-output[output$configuration==2,]
meanGscore[i]<-mean(C2[C2$technique=="Amasaki15",]$gscore)     

i=3
C3<-output[output$configuration==3,]
meanGscore[i]<-mean(C3[C3$technique=="Amasaki15",]$gscore)     

i=4
C4<-output[output$configuration==4,]
meanGscore[i]<-mean(C4[C4$technique=="Amasaki15",]$gscore)     

###Watanabe
i=5
C1<-output[output$configuration==1,]
meanGscore[i]<-mean(C1[C1$technique=="Watanabe08",]$gscore)

i=6
C2<-output[output$configuration==2,]
meanGscore[i]<-mean(C2[C2$technique=="Watanabe08",]$gscore)     

i=7
C3<-output[output$configuration==3,]
meanGscore[i]<-mean(C3[C3$technique=="Watanabe08",]$gscore)     

i=8
C4<-output[output$configuration==4,]
meanGscore[i]<-mean(C4[C4$technique=="Watanabe08",]$gscore)    

###CamargoCruz09
i=9
C1<-output[output$configuration==1,]
meanGscore[i]<-mean(C1[C1$technique=="CamargoCruz09",]$gscore)

i=10
C2<-output[output$configuration==2,]
meanGscore[i]<-mean(C2[C2$technique=="CamargoCruz09",]$gscore)     

i=11
C3<-output[output$configuration==3,]
meanGscore[i]<-mean(C3[C3$technique=="CamargoCruz09",]$gscore)     

i=12
C4<-output[output$configuration==4,]
meanGscore[i]<-mean(C4[C4$technique=="CamargoCruz09",]$gscore)    

###Nam15
i=13
C1<-output[output$configuration==1,]
meanGscore[i]<-mean(C1[C1$technique=="Nam15",]$gscore)

i=14
C2<-output[output$configuration==2,]
meanGscore[i]<-mean(C2[C2$technique=="Nam15",]$gscore)     

i=15
C3<-output[output$configuration==3,]
meanGscore[i]<-mean(C3[C3$technique=="Nam15",]$gscore)     

i=16
C4<-output[output$configuration==4,]
meanGscore[i]<-mean(C4[C4$technique=="Nam15",]$gscore)    

###Ma12
i=17
C1<-output[output$configuration==1,]
meanGscore[i]<-mean(C1[C1$technique=="Ma12",]$gscore)

i=18
C2<-output[output$configuration==2,]
meanGscore[i]<-mean(C2[C2$technique=="Ma12",]$gscore)     

i=19
C3<-output[output$configuration==3,]
meanGscore[i]<-mean(C3[C3$technique=="Ma12",]$gscore)     

i=20
C4<-output[output$configuration==4,]
meanGscore[i]<-mean(C4[C4$technique=="Ma12",]$gscore)  


################################# Convert to Latex Table ##################################
table6<-data.frame(meanfscores,meanMCC, meanROC,meanGscore)
library(xtable)
xtable(table6)
print(xtable(table6, type = "latex"), file = "Table6.tex")