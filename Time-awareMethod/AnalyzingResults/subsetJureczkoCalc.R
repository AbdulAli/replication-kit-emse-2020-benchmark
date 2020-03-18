config1 = data[data$configuration == 1,]

leftSubset <- subset(config1, ( time > 0 & time < 8 & windowSize == 1) 
                  | (time >0 & time < 7 & windowSize == 2)
                  | (time>0 & time < 6 & windowSize == 3)
                  | (time>0 & time < 5 & windowSize == 4)
                  | (time>0 & time < 4 & windowSize == 5)
                  | (time>0 & time < 3 & windowSize == 6)
                  | (time>0 & time < 2 & windowSize == 7))

rightSubset <- subset(config1, ( time > 9 & time < 18 & windowSize == 1) 
                      | (time > 10 & time < 18 & windowSize == 2)
                      | (time> 11 & time < 18 & windowSize == 3)
                      | (time> 12 & time < 18 & windowSize == 4)
                      | (time> 13 & time < 18 & windowSize == 5)
                      | (time> 14 & time < 18 & windowSize == 6)
                      | (time> 15 & time < 18 & windowSize == 7)
                      | (time> 16 & time < 18 & windowSize == 8))

dfLeft<-summarise_at(group_by(leftSubset,technique,configuration,windowSize,time),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))

fscoreSdLeft<-summarise_at(group_by(dfLeft,technique,configuration),vars(fscore),funs(sd(.,na.rm=TRUE)))



dfRight<-summarise_at(group_by(rightSubset,technique,configuration,windowSize,time),vars(fscore,mcc,roc,gscore),funs(mean(.,na.rm=TRUE)))

fscoreRight<-summarise_at(group_by(dfRight,technique,configuration),vars(fscore),funs(sd(.,na.rm=TRUE)))


original <- c(fscoreSd[fscoreSd$configuration==1,]$fscore)

firstSubset <- c(fscoreSdLeft$fscore)
secondSubset <- c(fscoreRight$fscore)

comparison <- data.frame(original = original, subset1 = firstSubset, subset2 = secondSubset)
